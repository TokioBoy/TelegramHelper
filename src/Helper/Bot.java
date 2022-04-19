package Helper;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

    public void sndMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        //Разметка Markdown должна быть отключена если делать погодник
        //sendMessage.enableMarkdown(false);!!!!
        //Возможно сделать разметку Html
        //sendMessage.enableHtml(true);!!!!
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
}
    @Override
    public void onUpdateReceived(Update update) {
        ModelforWeather model = new ModelforWeather();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                //Отправляет сообщение в ответ
//                case "/help":
//                    sndMsg(message, "Чем могу помочь?");
//                    break;
                case "/start":
                    sndMsg(message, "Привет , друг.Я - твой личный помощник. Для получения информации  , напиши /help , а для списка команд /commands");
                    break;
                case "/help":
                    sndMsg(message, "Привет , кнопки на твоей виртуальной клавиатуре переведут тебя на соответствующий сайт " + "\n" + "Также на данный момент доступен погодник который активируеться автоматически когда ты введешь нужный тебе город на английском языке");
                    break;
                case "/info":
                    sndMsg(message, "Я бот созданный пользователем TokioBoy, способен помогать тебе быстрее  взаимодействовать с интернетом");
                    break;
                case "/commands":
                    sndMsg(message, "/info");
                    break;
                case "":
                    break;
                case "  ":
                    break;
                case "d":
                    break;
                case "/Google":
                    try {
                        Desktop d = Desktop.getDesktop();

                        d.browse(new URI("http://www.google.com"));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (URISyntaxException use) {
                        use.printStackTrace();
                    }
                    break;
                case "/YouTube":
                    try {
                        Desktop d = Desktop.getDesktop();

                        d.browse(new URI("https://www.youtube.com"));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (URISyntaxException use) {
                        use.printStackTrace();
                    }
                    break;
            }
            //Сообщает погоду , если пользователь отправил в сообщении только город на английском
            try {
                sndMsg(message, WeatherInfo.getWeather(message.getText(), model));
            } catch (IOException e) {

            }
        }
    }


    //кнопки
    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
//Масив кнопок
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
// Наши кнопки
        keyboardFirstRow.add(new KeyboardButton("/Google"));
        keyboardFirstRow.add(new KeyboardButton("/YouTube"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return "HelperGet90Bot";
    }

    @Override
    public String getBotToken() {
        return "1734804865:AAGSOeeeLrz0T3Hjsy7O_x-_NQ-oiLuTcsM";
    }
}
