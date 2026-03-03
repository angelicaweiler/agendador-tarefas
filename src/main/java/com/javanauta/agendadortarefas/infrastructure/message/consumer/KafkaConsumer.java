package com.javanauta.agendadortarefas.infrastructure.message.consumer;

import com.javanauta.agendadortarefas.business.TarefasService;
import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TarefasService tarefasService;

    @KafkaListener(topics = "topico.status",
    groupId = "agendador-tarefas",
    containerFactory = "kafkaListenerContainerFactory")
    public void receberMensagem(TarefasDTO tarefasDTO){
        tarefasService.alteraStatus(tarefasDTO.getStatusNotificacaoEnum(), tarefasDTO.getId());
    }
}
