package com.unilopers.cinema.service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unilopers.cinema.model.Homologacao;
import com.unilopers.cinema.repository.HomologacaoRepository;

@Service
public class HomologacaoAsyncService {

    private static final Logger logger = LoggerFactory.getLogger(HomologacaoAsyncService.class);

    @Autowired
    private HomologacaoRepository homologacaoRepository;

    @Async
    @Transactional
    public void processarLaudoTecnico(Homologacao homologacao) {
        try {
            String threadName = Thread.currentThread().getName();

            logger.info("[HOMOLOGAÇÃO - INÍCIO] Thread: {} | Processando laudo para homologação ID: {}",
                    threadName, homologacao.getId());

            // Simula análise de requisitos técnicos
            Thread.sleep(5000);

            homologacao.setStatusValidacao("Aprovado");
            homologacaoRepository.save(homologacao);

            logger.info("[HOMOLOGAÇÃO - CONCLUÍDA] ID: {} | Status atualizado para: Aprovado | Thread: {}",
                    homologacao.getId(), threadName);

        } catch (InterruptedException e) {
            logger.error("[HOMOLOGAÇÃO - FALHA] Interrupção na thread para homologação ID: {}", homologacao.getId());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("[HOMOLOGAÇÃO - ERRO CRÍTICO] {}", e.getMessage());
        }
    }
}