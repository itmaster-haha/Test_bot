package settings;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import jakarta.annotation.PostConstruct;

@SuppressWarnings("deprecation")
@Component
public class CommandBot extends TelegramLongPollingBot{


    @Override
    public String getBotUsername() {
        return "TaskgamerBot";
    }

    @Override
    public String getBotToken(){
        return "8419784984:AAFJzwJXteMtxHMMf1pPWFaa0L6B2AQ2Jx8";
    }

    @Autowired
    private DataSource dataSource;
    
    @PostConstruct
    public void init() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ База подключена!");
        } catch (Exception e) {
            System.out.println("❌ Ошибка БД: " + e.getMessage());
        }
    }

    private void DefaultText(Message command, String text) {
        try {
            execute(
                SendMessage.builder()
                .chatId(command.getChatId())
                .parseMode("Markdown")
                .text(text)
                .build());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardButton ButtonCreate(String Btext, String Call) {

        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(Btext);
        button.setCallbackData(Call);
        return button;

    }

    private InlineKeyboardMarkup CreateKeyBoard (List<String[]> buttons) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        int i;
        int count = buttons.size();
        for(i=0; i<count; i++) {
            button = ButtonCreate(buttons.get(i)[0], buttons.get(i)[1]);
            keyboardButtonRow.add(button);
        }
        rowList.add(keyboardButtonRow);
        keyboard.setKeyboard(rowList);
        return keyboard;
    }

    private void sendWithOutURL(Message message, List<String[]> buttons, String Qtext) {

        InlineKeyboardMarkup keyboard = CreateKeyBoard (buttons);
        try {
                execute(
                    SendMessage.builder()
                    .chatId(message.getChatId())
                    .parseMode("Markdown")
                    .text(Qtext)
                    .replyMarkup(keyboard)
                    .build());
            }
                catch (TelegramApiException e) {
                    e.printStackTrace();
                }

    }

    @Override
    public void onUpdateReceived(Update update) {
        
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String messageText = update.getMessage().getText();
            Message command = update.getMessage();

            switch (messageText) {

                case "/start":
                    startAnswer(command);
                    break;
                default:
                    DefaultText(command, "I don't understand");
                    break;
            }
        }

        else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            Message command = update.getCallbackQuery().getMessage();

            switch (callbackData) {
                case "first_command":
                    DefaultText(command, "You don't have profile now");
                    break;
                case "fourth_command":
                    DefaultText(command, "Durak");
                    break;
                default:
                    DefaultText(command, "It's not good idea");
                    break;
}
        }

    }

    public void startAnswer(Message command) {

        List<String[]> buttons = new ArrayList<>();

        buttons.add(new String[]{"Profile", "first_command"});
        buttons.add(new String[]{"Add the task", "second_command"});
        buttons.add(new String[]{"Get tasks", "third_command"});
        buttons.add(new String[]{"Alibek", "fourth_command"});

        sendWithOutURL(command, buttons, "Are you stuped?");
        
    }
    
}
