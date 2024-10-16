package com.tsm.rule.ia.llminteraction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenericiInteraction {

    private final OllamaChatModel ollamaChatModel;

    public String analizzaAcquisti(){
        log.info("AnalizzaAcquisti llm integration start");

        var iaResp = ollamaChatModel.call(new Prompt("")).getResult().getOutput().getContent();

        log.info("AnalizzaAcqisti llm integration ended successfully");
        return iaResp;
    }



    public String analizzaVendite(){
        log.info("AnalizzaVendita llm integration start");

        var iaResp = ollamaChatModel.call(new Prompt("")).getResult().getOutput().getContent();

        log.info("AnalizzaVendita llm integration ended successfully");
        return iaResp;
    }
}
