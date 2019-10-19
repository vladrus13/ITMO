package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Colors;
import ru.itmo.tpl.model.Link;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", Colors.GREEN),
            new User(2, "tourist", "Genady Korotkevich", Colors.RED),
            new User(3, "emusk", "Elon Musk", Colors.BLUE),
            new User(5, "pashka", "Pavel Mavrin", Colors.RED),
            new User(7, "geranazavr555", "Georgiy Nazarov", Colors.GREEN),
            new User(11, "cannon147", "Erofey Bashunov", Colors.BLUE)
    );

    private static final List<Link> LINKS = Arrays.asList(
            new Link(1, "/index", "Index"),
            new Link(2, "/misc/help", "Help"),
            new Link(3, "/users", "Users")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, 1, "Polygon update",
                    "Привет, Codeforces!\n" +
                    "\n" +
                    "Вот несколько последних улучшений здесь и в Polygon.\n" +
                    "\n" +
                    "Слабые и утекшие пароли\n" +
                    "Мы часто слышим об утечках паролей от различных сервисов. Учитывая, что иметь одинаковые пароли распространённая (но небезопасная) практика, на Codeforces и в Полигоне были внедрены улучшения для определения слабых или утекших паролей. Если сверху сайта вы видите плашку, что ваш пароль небезопасен, то просто тут же смените его.\n" +
                    "\n" +
                    "Указание типа раунда при создании предложения о контесте\n" +
                    "Этот пункт немного лучше упорядочивает работу с авторами. При создании предложения контеста, пожалуйста, указывайте тип контеста. Оставьте поле пустым только, если вы совсем не уверены в типе раунда (что странно).\n" +
                    "\n" +
                    "Календарь\n" +
                    "Исправлены ошибки при синхронизации официальных контестов Codeforces в календаре. Теперь всё должно быть чётко.\n" +
                    "\n" +
                    "Я доверяю этому пользователю\n" +
                    "В настоящее время Codeforces предоставляет развитую инфраструктуру для организации контестов, кружков и тренировок. С помощью доменных групп и мэшапов абсолютно штатными средствами проводятся соревнования разного уровня и официальности. Дошло до того, что организаторы иногда не являются регулярными участниками раундов Codeforces и не имеют прав на некоторые из действий. Теперь любой красный участник может подтвердить своё доверие другому аккаунту, и тот получит права на: написание комментариев/постов, создание приватных групп, создание мэшапов. Я надеюсь это избавит меня от определенной рутины обработки подобных запросов.\n" +
                    "\n" +
                    "Подтверждение через email при входе в Polygon\n" +
                    "В случае, если ваш текущий IP-адрес и браузер давно не использовались для входа в Polygon, то вам может быть предложено войти с подтверждением email. В таком случае, просто перейдите по секретной ссылке, которая придёт вам на почту."),
            new Post(2, 2, "Codeforces Global Round 5",
                    "Всем привет!\n" +
                            "\n" +
                            "Скоро состоится Codeforces Global Round 5, время начала: среда, 16 октября 2019 г. в 17:35.\n" +
                            "\n" +
                            "Это пятый раунд из серии Codeforces Global Rounds, которая проводится при поддержке XTX Markets. В раундах могут участвовать все, рейтинг тоже будет пересчитан для всех.\n" +
                            "\n" +
                            "Соревнование продлится 2 часа 30 минут, вас ожидает 8 задач, при этом одна из задач будет представлена в двух версиях.\n" +
                            "\n" +
                            "Разбалловка: 500 — 750 — (750 + 750) — 2000 — 2500 — 3000 — 3750 — 4000\n" +
                            "\n" +
                            "Призы в этом раунде:\n" +
                            "\n" +
                            "30 лучших участников получат футболки.\n" +
                            "20 футболок будут разыграны случайным образом среди участников с 31-го по 500-е место.\n" +
                            "Призы в серии из 6 раундов в 2019 году:\n" +
                            "\n" +
                            "За каждый раунд лучшим 100 участникам начисляются баллы согласно таблице.\n" +
                            "Итоговый результат участника равны сумме баллов для четырех лучших выступлений этого участника.\n" +
                            "Лучшие 20 участников по итоговым результатам получают толстовки и сертификаты с указанием места.\n" +
                            "Задачи раунда разработаны мной, а вот список людей, которым нельзя принимать участие в этом раунде, потому что они знали задачи заранее:\n" +
                            "\n" +
                            "KAN, Endagorion, arsijo, Rox, lperovskaya, Aleks5d, Learner99, MikeMirzayanov.\n" +
                            "\n" +
                            "Так совпало, что это одновременно и те самые люди, которым я благодарен за то, что этот раунд такой, какой он есть.\n" +
                            "\n" +
                            "Раунд будет идеально сбалансирован. (no)"),
            new Post(3, 7, "JO", "JOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJO\nJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO\nJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJ\nOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJOJO"),
            new Post(4, 5, "ANONCE", "Всем привет!\n" +
                    "\n" +
                    "19 октября в 15:00 состоится вторая командная интернет-олимпиада для школьников. Приглашаем вас принять в ней участие! В этот раз вам предстоит помочь или помешать Джокеру.\n" +
                    "\n" +
                    "Продолжительность олимпиады — 3 часа в базовой и 5 часов в усложненной номинациях. Вы сами можете выбрать номинацию, в которой будете участвовать. Не забудьте зарегистрироваться на цикл командных интернет-олимпиад в этом сезоне перед началом олимпиады, если не сделали этого ранее. Обратите внимание, что для участия в командных олимпиадах, нужно зарегистрировать команду (по ссылке \"Новая команда\"). Команда может содержать от 1 до 3 человек. Дополнительную информацию, а также расписание всех предстоящих командных интернет-олимпиад можно посмотреть на страничке интернет-олимпиад.\n" +
                    "\n" +
                    "Условия появятся на сайте в момент начала олимпиады, а также на вкладке \"Файлы\" в тестирующей системе. Тестирующая система находится по адресу pcms.itmo.ru.\n" +
                    "\n" +
                    "Олимпиаду для вас подготовили Николай Будин (Nebuchadnezzar), Ильдар Гайнуллин (300iq), Арсений Кириллов (senek_k), Дмитрий Саютин (_kun_), Михаил Анопренко (manoprenko), Даниил Орешников (doreshnikov), Григорий Шовкопляс (GShark) и Дмитрий Гнатюк (ima_ima_go).\n" +
                    "\n" +
                    "Для связи с жюри можно использовать адрес электронной почты iojury@gmail.com.\n" +
                    "\n" +
                    "Удачи!"),
            new Post(5, 1, "Finish Post", "Goodbye? Or not?")
    );

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<Link> getLinks() { return LINKS;}

    private static List<Post> getPosts() {return POSTS;}

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("links", getLinks());
        data.put("posts", getPosts());
        for (User user : getUsers()) {
            if (Long.toString(user.getId()).equals(data.get("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
