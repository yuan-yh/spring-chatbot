//package com.ai.aidemo.controller;
//
//import org.springframework.ai.chat.messages.AssistantMessage;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//
//@RestController
//public class DeepSeekController {
//
//    private final OpenAiChatModel chatModel;
//    private final String systemMessage;
//
//    @Autowired
//    public DeepSeekController(OpenAiChatModel chatModel, String systemMessage) {
//        this.chatModel = chatModel;
//        this.systemMessage = systemMessage;
//    }
//
//    @GetMapping("/ds/generate")
//    public ResponseEntity<String> generate(
//            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        try {
//            Prompt prompt = new Prompt(List.of(
//                    new SystemMessage(systemMessage),
//                    new UserMessage(message)
//            ));
//            String response = chatModel.call(prompt).getResult().getOutput().getText();
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError()
//                    .body("Failed to generate response: " + e.getMessage());
//        }
//    }
//
//    @GetMapping(value = "/ds/generateStream", produces = "text/plain; charset=UTF-8")
//    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
//        Prompt prompt = new Prompt(List.of(
//                new SystemMessage(systemMessage),
//                new UserMessage("robin是誰"),
//                new AssistantMessage("Robin 是一款专为农场管理设计的智能 AI 助手，旨在帮助农民和农业从业者更高效地管理日常任务、优化生产流程并解决农业相关问题。 ### **Robin 的功能包括**： 1. **农场管理支持**：协助规划种植、养殖计划，跟踪作物生长或牲畜健康状况。 2. **数据记录与分析**：帮助记录天气、土壤、产量等数据，并提供优化建议。 3. **问题解答**：回答农业技术问题（如病虫害防治、施肥建议等）。 4. **自动化提醒**：提供灌溉、施肥、收割等关键节点的提醒。 5. **市场与政策信息**：提供农产品市场趋势或农业政策更新（若集成相关数据源）。 ### **如何使用 Robin？** - 直接通过文本或语音输入问题（如 *“如何防治小麦锈病？”* 或 *“记录今天的牛奶产量”*）。 - 与农场管理软件或 IoT 设备（如传感器、无人机）联动（需系统支持）。 Robin 的目标是成为你的“数字农业伙伴”，让农场运营更轻松、更智能！ \uD83C\uDF3E\uD83E\uDD16 如果有具体需求或想了解某项功能，随时告诉我！"),
//                new UserMessage(message)
//        ));
//        return this.chatModel.stream(String.valueOf(prompt));
//    }
//}