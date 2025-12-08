package com.fernando.vote.functions.services.impl;

import com.fernando.vote.functions.events.ServiceBusEvent;
import com.fernando.vote.functions.events.impl.VoteServiceBusEventImpl;
import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.models.containers.PoolId;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.repository.CacheRepository;
import com.fernando.vote.functions.repository.impl.SurveyRepositoryImpl;
import com.fernando.vote.functions.repository.impl.CacheRedisRepositoryImpl;
import com.fernando.vote.functions.services.IVoteService;

import java.util.Map;

public class IVoteServiceImpl implements IVoteService {
    private final SurveyRepository surveyRepository=new SurveyRepositoryImpl();
    private final CacheRepository cacheRepository =new CacheRedisRepositoryImpl();
    private final ServiceBusEvent serviceBusEvent=new VoteServiceBusEventImpl();
    @Override
    public long registerVote(Vote vote) {
        String keyVote="votes:"+vote.getPoolId();
        long count= cacheRepository.createHashSet(keyVote,vote.getOptionId());
        serviceBusEvent.sendQueue(vote.getPoolId());
        return count;
    }

    @Override
    public void synVotes(PoolId poolId) {
        long totalVotes=0L;
        String keyVote="votes:"+ poolId.getPoolId();
        Map<String,String> votes= cacheRepository.getHashSet(keyVote);
        Pool pool =surveyRepository.getSurvey(poolId.getPoolId());
        for (Option opt: pool.getOptions()){
            if(votes.containsKey(opt.getOptionId())){
                long count=Long.parseLong(votes.get(opt.getOptionId()));
                opt.setVotes(count);
                totalVotes+=count;
            }
        }
        long diff=totalVotes- pool.getTotalVotes();
        if(diff>0){
            pool.setTotalVotes(totalVotes);
            surveyRepository.save(pool);
        }
    }


}
