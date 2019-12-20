CREATE TABLE `post` (
  `id` bigint(20) NOT NULL,
  `creation_time` datetime DEFAULT NULL,
  `text` longtext NOT NULL,
  `title` varchar(60) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `post` (`id`, `creation_time`, `text`, `title`, `user_id`) VALUES
(1, '2019-12-16 17:59:44', 'Всем привет!\r\n\r\nСкоро состоится Codeforces Global Round 6, время начала: вторник, 17 декабря 2019 г. в 18:05.\r\n\r\nЭто шестой раунд из серии Codeforces Global Rounds, которая проводится при поддержке XTX Markets. В раундах могут участвовать все, рейтинг тоже будет пересчитан для всех.\r\n\r\nСоревнование продлится 2 часа 30 минут, вас ожидает 8 задач, при этом одна из задач будет представлена в двух версиях. Разбалловка будет объявлена незадолго до старта раунда.', 'Codeforces Global Round 6', 1),
(2, '2019-12-16 18:00:50', 'В этом семестре записал на видео все лекции курса \"Алгоритмы и структуры данных\", который я читаю в ИТМО. Лекции стримились в прямом эфире на твич и потом выкладывались на ютуб.\r\n\r\nКурс скорее академический, а не олимпиадный, но думаю многим начинающим (и не только) олимпиадникам тоже будет интересно. Например, эти лекции.', 'Видеолекции моего курса в ИТМО', 2),
(3, '2019-12-16 18:00:39', 'Привет! В четверг, 12 декабря 2019 г. в 16:35 начнётся Codeforces Round #605 (Div. 3) — очередной Codeforces раунд для третьего дивизиона. В этом раунде будет 6 или 7 задач (или 8), которые подобраны по сложности так, чтобы составить интересное соревнование для участников с рейтингами до 1600. Однако все желающие, чей рейтинг 1600 и выше могут зарегистрироваться на раунд вне конкурса.\r\n\r\nРаунд пройдет по правилам образовательных раундов. Таким образом, во время раунда задачи будут тестироваться на предварительных тестах, а после раунда будет 12-ти часовая фаза открытых взломов. Я постарался сделать приличные тесты — так же как и вы буду расстроен, если у многих попадают решения после окончания контеста.\r\n\r\nВам будет предложено 6 или 7 (или 8) задач и 2 часа на их решение.\r\n\r\nШтраф за неверную попытку в этом раунде (и последующих Div. 3 раундах) будет равняться 10 минутам.', 'Codeforces Round #605 (Div. 3)', 1);

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `admin` bit(1) NOT NULL,
  `creation_time` datetime DEFAULT NULL,
  `login` varchar(24) NOT NULL,
  `name` varchar(100) NOT NULL,
  `passwordSha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `admin`, `creation_time`, `login`, `name`, `passwordSha`) VALUES
(1, b'0', '2019-12-16 17:57:45', 'mike', 'Mike Mirzayanov', '783a78e11175b9d9699599b39c475ab77950ee31'),
(2, b'0', '2019-12-16 17:58:40', 'tester', 'Tester Testerov', '4c6f0278e40dbd369cf6406b29123b18cc3df578');

ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKew1hvam8uwaknuaellwhqchhb` (`login`);

ALTER TABLE `post`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `post`
  ADD CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
