package com.fernando.vote.functions.services.impl;

import com.fernando.vote.functions.events.ServiceBusEvent;
import com.fernando.vote.functions.events.impl.VoteServiceBusEventImpl;
import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.containers.SurveyId;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.repository.VoteRepository;
import com.fernando.vote.functions.repository.impl.SurveyRepositoryImpl;
import com.fernando.vote.functions.repository.impl.VoteRepositoryImpl;
import com.fernando.vote.functions.services.IVoteService;

import java.util.Map;

public class IVoteServiceImpl implements IVoteService {
    private final SurveyRepository surveyRepository=new SurveyRepositoryImpl();
    private final VoteRepository voteRepository=new VoteRepositoryImpl();
    private final ServiceBusEvent serviceBusEvent=new VoteServiceBusEventImpl();
    @Override
    public long registerVote(Vote vote) {
        String keyVote="votes:"+vote.getSurveyId();
        long count=voteRepository.saveVote(keyVote,vote.getOptionId());
        serviceBusEvent.sendQueue(vote.getSurveyId());
        return count;
    }

    @Override
    public void synVotes(SurveyId surveyId) {
        long totalVotes=0L;
        String keyVote="votes:"+surveyId.getSurveyId();
        Map<String,String> votes= voteRepository.getVotes(keyVote);
        Survey survey=surveyRepository.getSurvey(surveyId.getSurveyId());
        for (Option opt: survey.getOptions()){
            if(votes.containsKey(opt.getOptionId())){
                long count=Long.parseLong(votes.get(opt.getOptionId()));
                opt.setVotes(count);
                totalVotes+=count;
            }
        }
        long diff=totalVotes-survey.getTotalVotes();
        if(diff>0){
            survey.setTotalVotes(totalVotes);
            surveyRepository.save(survey);
        }
    }


}
