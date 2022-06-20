package com.ipdev.evote.grpc.server;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Timestamp;
import com.google.rpc.Status;
import com.ipdev.evote.data.dto.AbstractResponseDto;
import com.ipdev.evote.data.dto.BlankDto;
import com.ipdev.evote.data.dto.PartyDto;
import com.ipdev.evote.data.dto.VoterDto;
import com.ipdev.evote.data.model.Blank;
import com.ipdev.evote.data.model.Party;
import com.ipdev.evote.data.service.BlankService;
import com.ipdev.evote.data.service.PartyService;
import com.ipdev.evote.data.service.VoterService;
import com.ipdev.evote.grpc.client.EVoteServiceGrpc;
import com.ipdev.evote.grpc.client.FiscalRequest;
import com.ipdev.evote.grpc.client.FiscalResponse;
import com.ipdev.evote.grpc.client.PartyRequest;
import com.ipdev.evote.grpc.client.PartyResponse;
import com.ipdev.evote.grpc.client.RowBlank;
import com.ipdev.evote.grpc.client.VoteRequest;
import com.ipdev.evote.grpc.client.VoteResponse;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class EVoteServiceImpl extends EVoteServiceGrpc.EVoteServiceImplBase {
    @Autowired
    private BlankService blankService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private VoterService voterService;
    /**
     *
     */
    @Override
    public void check(FiscalRequest request, StreamObserver<FiscalResponse> responseObserver) {
        String fiscalId = request.getFiscalId();
        VoterDto voter = voterService.check(fiscalId);
        FiscalResponse response = FiscalResponse.newBuilder()
                .setVoted(voter.isVoted())
                .build();
        if(voter.isVoted()) {
            Status status = getStatus(voter, response);
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        } else {
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     */
    @Override
    public void getAvailableChoices(PartyRequest request, StreamObserver<PartyResponse> responseObserver) {
        String token = request.getToken();
        // check if token is valid
        // db call for available parties
        //set list of parties depends on call above
        List<RowBlank> list = new ArrayList<>();
        PartyDto parties = partyService.getParties(true);
        for (Party party : parties.getParties()) {
            RowBlank rowBlank = RowBlank.newBuilder()
                    .setId((int) party.getId())
                    .setLeader(party.getLeader())
                    .setParty(party.getParty())
                    .setImage(party.getId() == 3 ? ByteString.copyFrom("byte-image-encoded-from-string".getBytes(Charset.defaultCharset())) : ByteString.EMPTY)
                    .build();
            list.add(rowBlank);
        }

        PartyResponse response = PartyResponse.newBuilder()
                .addAllVoted(list)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     */
    @Override
    public void vote(VoteRequest request, StreamObserver<VoteResponse> responseObserver) {
        Timestamp inserted = request.getInserted();
        LocalDateTime localDateTime = LocalDateTime
                .ofInstant(Instant.ofEpochSecond(inserted.getSeconds(), inserted.getNanos()),
                        ZoneId.systemDefault());
        RowBlank voted = request.getVoted();
        Party party = new Party();
        party.setId(voted.getId());
        Blank blank = new Blank();
        blank.setTimestamp(localDateTime);
        blank.setVoted(party);
        BlankDto saved = blankService.save(blank);
        VoteResponse response = VoteResponse.newBuilder()
                                            .setSuccess(true)
                                            .build();
        if(saved.getBlanks().isEmpty()) {
            Status status = getStatus(saved, response);
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        } else {
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    /**
     */
    @Override
    public void finishVote(FiscalRequest request, StreamObserver<FiscalResponse> responseObserver) {
        String fiscalId = request.getFiscalId();
        // update db
        VoterDto save = voterService.save(fiscalId);
        FiscalResponse response = FiscalResponse.newBuilder().setVoted(save.isVoted()).build();
        if(!save.isVoted()) {
            Status status = getStatus(save, response);
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        } else {
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    private Status getStatus(AbstractResponseDto dto, GeneratedMessageV3 response) {
        Status status = Status.newBuilder()
                .setCode(Integer.valueOf(dto.geteCode()))
                .setMessage(dto.getErrors().stream().collect(Collectors.joining(",")))
                .addDetails(Any.pack(response))
                .build();
        return status;
    }
}
