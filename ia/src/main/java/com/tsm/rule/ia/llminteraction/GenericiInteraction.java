package com.tsm.rule.ia.llminteraction;

import com.tsm.rule.ia.io.request.AcquistiRequest;
import com.tsm.rule.ia.io.request.AcquistoPokemonRequest;
import com.tsm.rule.ia.io.request.VenditaPokemonRequest;
import com.tsm.rule.ia.io.request.VenditeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenericiInteraction {

    private final OllamaChatModel ollamaChatModel;



    public String analizzaAcquisti(String richiesta, List<AcquistoPokemonRequest> request){
        log.info("AnalizzaAcquisti llm integration start");
        // setto il prompt con la request del json in allegato
        var richiestaUtente = richiesta + "This is the json u have to analize: " + request;
        // configuro la richiesta per il prompt
        var userPromptTemplate = new PromptTemplate(richiestaUtente);
        var userMessage = userPromptTemplate.createMessage();

        // setto prompt logico prima di richiesta utente con nome
        var systemText = """
            You are a helpful AI assistant that helps people find information of about some items . Your name is {name}
            In your first response, greet the user, quick summary of answer and then do not repeat it. 
            Next, you should reply to the user's request. 
            Finish with thanking the user for asking question in the end.
            """;

        var systemPromptTemplate = new SystemPromptTemplate(systemText);
        // settto il {name} all'interno del sstemTexg
        var systemMessage = systemPromptTemplate.createMessage(Map.of("name", "Friday"));
        // setto prompt finale
        var prompt = new Prompt(List.of(userMessage, systemMessage));
        // chiamata a ia
        var iaResp = ollamaChatModel.call(prompt).getResult().getOutput().getContent();

        // torno resp
        log.info("AnalizzaAcqisti llm integration ended successfully");
        return iaResp;
    }



    public String analizzaVendite(String prompt, VenditaPokemonRequest request){
        log.info("AnalizzaVendita llm integration start");

        var iaResp = ollamaChatModel.call(new Prompt("")).getResult().getOutput().getContent();

        log.info("AnalizzaVendita llm integration ended successfully");
        return iaResp;
    }
}
