package com.tal.wangxiao.conan.agent.service;

/**
 * Agent diff service
 *
 * @author mtx
 * @date 2021-12-21
 */
public interface AgentDiffService {

	
	void startDiff(Integer taskExcutionId,Integer recordId,Integer replayId,Integer diffId) throws Exception;
}
