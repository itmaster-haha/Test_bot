package settings;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private void sendWithOutURL(Message message) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText("Puk");
        button.setCallbackData("Start 0");

        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        keyboardButtonRow.add(button);
        
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow);

        keyboard.setKeyboard(rowList);

        try {
                execute(
                    SendMessage.builder()
                    .chatId(message.getChatId())
                    .parseMode("Markdown")
                    .text("OK?")
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
            long chatID = update.getMessage().getChatId();

            switch (messageText) {

                case "/start":
                    startAnswer(command);
                    break;
                default:
                    try {
                        execute(
                            SendMessage.builder()
                            .chatId(command.getChatId())
                            .parseMode("Markdown")
                            .text(" HOHO iT'S TEST")
                            .build());
                    }
                        catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    break;
            }
        }

        else if (update.hasCallbackQuery()) {
            
            if (update.getCallbackQuery().getData().equals("Start 0")) {

                try {
            execute(
                SendMessage.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .parseMode("Markdown")
                .text("_Hello, I'm your Game Halper ToDo_")
                .build());
        }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }

            }
        }

    }

    public void startAnswer(Message command) {
        sendWithOutURL(command);
    }
    
}
