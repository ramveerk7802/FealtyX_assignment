package com.rvcode.Fealtyxassignment.services.clients;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="ollama-client", url = "http://localhost:11434")
public interface OllamaFiegnClient {

    @PostMapping("/api/generate")
    public Map<String,Object> generatedText(@RequestBody Map<String,Object> request);
}
