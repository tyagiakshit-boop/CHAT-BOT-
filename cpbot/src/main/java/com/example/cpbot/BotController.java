package com.example.cpbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class BotController extends TelegramLongPollingBot {

    private final ApiService contestService;
    private final String botUsername;

    public BotController(@Value("${telegram.bot.token}") String botToken,
                         @Value("${telegram.bot.username}") String botUsername,
                         ApiService contestService) {
        super(botToken);
        this.botUsername = botUsername;
        this.contestService = contestService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
           System.out.println("🤖 I heard a message from Telegram: " + messageText);
            if (messageText.startsWith("/start")) {
                sendMessage(chatId, "Hello! I am your CP Contest Tracker.\n\nUse /contests for all upcoming platforms.\nUse /tomorrow for events starting in the next 24 hours.");
            } else if (messageText.startsWith("/contests")) {
                sendContests(chatId, contestService.getAllUpcomingContests(), "Upcoming Contests");
            } else if (messageText.startsWith("/tomorrow")) {
                sendContests(chatId, contestService.getContestsTomorrow(), "Tomorrow's Events");
            } else {
                sendMessage(chatId, "Command not recognized. Try /contests or /tomorrow.");
            }
        }
    }

    private void sendContests(long chatId, List<DataModel> contests, String header) {
        StringBuilder sb = new StringBuilder("🏆 *" + header + "* 🏆\n\n");

        if (contests == null || contests.isEmpty()) {
            sb.append("No contests found right now!");
        } else {
            // Limit to 7 to prevent Telegram from blocking long messages
            contests.stream().limit(7).forEach(c -> {
                sb.append("🔹 *").append(c.name()).append("*\n");
                sb.append("🏢 Platform: ").append(c.site()).append("\n");

                String timeDisplay = c.startTime();
                try {
                    LocalDateTime utc = LocalDateTime.parse(c.startTime());
                    ZonedDateTime ist = utc.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
                    timeDisplay = ist.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a z"));
                } catch (Exception ignored) {
                    // Fallback to raw string if parsing fails
                }

                sb.append("⏰ Starts: ").append(timeDisplay).append("\n");
                sb.append("🔗 [Link](").append(c.url()).append(")\n\n");
            });
        }
        sendMessage(chatId, sb.toString());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("Markdown");
        message.setDisableWebPagePreview(true);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override


    public String getBotUsername() {
        return botUsername;
    }
}