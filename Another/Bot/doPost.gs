// 2018-2019 @vladrus13 - telegram. Please, if you want to ask me, choose this method/
// Attention! When you view the code, you may experience eye pain! 
// In that case, close immediately. Look at your own risk. 
// (P.S. Don't show the code to my programming teacher!)

function doPost(e) {
  var update = JSON.parse(e.postData.contents);
  if (update.hasOwnProperty('message')) {
    var msg = update.message;
    var message = msg.text;
    var chatId = msg.chat.id;
    var username = msg.from.username;
    if (msg.hasOwnProperty('entities') && msg.entities[0].type == 'bot_command') {
      var Id = getId(username);
      make_log(Id, message);
      set_chatId(Id, chatId);
      if (Id == -1) {
        sendMessage(chatId, "Sorry, I dont know you");
        return;
      }
      if (message.indexOf(String("/permission")) == 0) {
        permission(getPermission(Id));
      }
      if (message.indexOf(String("/add")) == 0) {
        add(chatId, message, Id);
      }
      if (message.indexOf(String("/remove")) == 0) {
        remove(chatId, message, Id);
      }
      if (message.indexOf(String("/info")) == 0) {
        info(chatId, Id);
      }
      if (message.indexOf(String("/alarm")) == 0) {
        alarm(chatId, message, Id);
      }
      if (message.indexOf(String("/mode")) == 0) {
        mode(chatId, message, Id);
      }
      if (message.indexOf(String("/bot_info")) == 0) {
        bot_info(chatId);
      }
      if (message.indexOf(String("/bclear_log")) == 0) {
        clear_log(chatId, Id);
      }
      if (message.indexOf(String("/p")) == 0) {
        prepare(chatId, message, Id);
      }
      if (message.indexOf(String("/c")) == 0) {
        call(chatId, message, Id);
      }
      if (message.indexOf(String("/help")) == 0) {
        help(chatId, Id);
      }
      if (message.indexOf(String("/link")) == 0) {
        link(chatId);
      }
      if (message.indexOf(String("/set_username")) == 0) {
        set_username(Id, message);
        sendMessage(chatId, "Your new username: " + getUsername(Id));
      }
      if (message.indexOf(String("/send_message")) == 0) {
        if (isAdmin(Id)) {
           sendAll(message);
           sendMessage(chatId, "Success send: " + message);
        }
      }
      if (message.indexOf(String("/vlad")) == 0) {
        sendMessage(chatId, "DO YOU KNOW DE WEY. KNOCK KNOCK KNOCK");
      }
    }
  }
}

function parser(message, p_l) {
  var tasks = [];
  message = String(message);
  var temp = "";
  for (var i = p_l; i < message.length; i++) {
    if (String(message[i]) != " ") {
      if (temp.length > 20) temp = "";
      temp = temp + message[i];
    } else {
      if (temp.length > 0 && temp.length < 20) {
        tasks.push(temp);
      }
      temp = "";
    }
  }
  if (temp.length > 0 && temp.length < 20) {
    tasks.push(temp);
  }
  return tasks;
}

function setValue(height, wight, sheet, value) {
  SpreadsheetApp.openById('1cLSB7agk9W0uroyQTHgwnmS_ikk36HTUJLbGtmjQpec').getSheets()[sheet].getRange(height, wight).setValue(value);
}

function setBackGround(height, wight, sheet, r, g, b) {
  SpreadsheetApp.openById('1cLSB7agk9W0uroyQTHgwnmS_ikk36HTUJLbGtmjQpec').getSheets()[sheet].getRange(height, wight).setBackgroundRGB(r, g, b);
}

function setValueFromAnotherSpread(t, h, wight, v, s) {
  SpreadsheetApp.openById(t).getSheets()[s].getRange(h, w).setValue(v);
}

function getValue(height, wight, sheet) {
  return SpreadsheetApp.openById('1cLSB7agk9W0uroyQTHgwnmS_ikk36HTUJLbGtmjQpec').getSheets()[sheet].getRange(height, wight).getValue();
}

function add(chatId, message, Id) {
  if (isStudent(Id)) {
    var tasks = parser(message, 4);
    var w = 7;
    var NUM = getNumMode(Id);
    while (true) {
      for (var i = 0; i < tasks.length; i++) {
        if (getValue(1, w, NUM) == tasks[i]) {
          setValue(Id + 1, w, NUM, "+"); 
        }
      }
      w++;
      if (getValue(1, w, NUM) == "#") {
        sendMessage(chatId, "Successful added:\n" + (tasks.length == 0 ? String("NOTHING!") : String(tasks)));
        return;
      }
    }
  } else {
    // TODO
    sendMessage(chatId, "Please, write @vladrus13");
    return;
  }
}

function remove(charId, message, Id) {
  if (isStudent(Id)) {
    var tasks = parser(message, 4);
    var w = 7;
    var NUM = getNumMode(Id);
    while (true) {
      for (var i = 0; i < tasks.length; i++) {
        if (getValue(1, w, NUM) == tasks[i]) {
          setValue(Id + 1, w, NUM, null); 
        }
      }
      w++;
      if (getValue(1, w, NUM) == "#") {
        sendMessage(chatId, "Successful removed:\n" + (tasks.length == 0 ? String("NOTHING!") : String(tasks)));
        return;
      }
    }
  } else {
    // TODO
    sendMessage(chatId, "Please, write @vladrus13");
    return;
  }
}

function help(chatId, Id) {
  var start = 3;
  var wi = 10;
  if (isStudent(Id)) {
    wi = 11;
  } else {
    wi = 12;
  }
  var writer = getValue(2, wi, 5) + "\nКоманды:\n\n";
  while (true) {
    if (getValue(start, wi, 5) != "#") {
    writer += getValue(start, 10, 5) + " \nШаблон использования: " + getValue(start, wi, 5) + " \n " + getValue(start + 1, wi, 5) + "\n\n";
    }
    start += 2;
    if (getValue(start, 10, 5) == "#") {
      sendMessage(chatId, writer);
      return;
    }
  }
}

function info(chatId, Id) {
  sendMessage(chatId, "Информация о Вас:\nid: " + Id + "\nUsername: " + getUsername(Id) + "\nName: " + getName(Id) + "\nPermission: " + getPermission(Id) + "\nMode: " + getMode(Id) + "\nAlarm: " + getAlarm(Id));
}

function prepare(chatId, message, Id) {
  if (isAdmin(Id)) {
    var mass = parser(message, 2);
    if (mass.length < 2) {
      sendMessage(chatId, "More then one arg!");
      return;
    } else {
      var newId = Number(mass[0]);
      var q = Number(mass[1]);
      var NUM = getNumMode(Id);
      var w = 7;
      while (true) {
        if (getValue(1, w, NUM) == q) {
          for (var i = 1; i < 100; i++) {
            setBackGround(i, w, NUM, 255, 255, 255);
          }
          setBackGround(newId + 1, w, NUM, 255, 255, 0); 
          sendMessage(chatId, getName(newId) + " успешно вызван на задачу " + q);
          return;
        }
        w++;
        if (getValue(1, w, NUM) == "#") {
          sendMessage(chatId, "Таска не найдена!");
          return;
        }
      }
    }
  } else {
    sendMessage(chatId, "You dont have permissions!");
  }
}

function call(chatId, message, Id) {
  if (isAdmin(Id)) {
    var mass = parser(message, 2);
    if (mass.length < 2) {
      sendMessage(chatId, "More then one arg!");
      return;
    } else {
      var newId = Number(mass[0]);
      var q = Number(mass[1]);
      var NUM = getNumMode(Id);
      var w = 7;
      while (true) {
        if (getValue(1, w, NUM) == q) {
          sendMessage(chatId, newId);
          for (var i = 1; i < 100; i++) {
            setBackGround(i, w, NUM, 255, 255, 255);
          }
          setBackGround(newId + 1, w, NUM, 0, 255, 0); 
          setValue(newId + 1, 5, NUM, getValue(newId + 1, 4, NUM) + 1);
          sendMessage(chatId, getName(newId) + " успешно выполнил задачу " + q);
          return;
        }
        w++;
        if (getValue(1, w, NUM) == "#") {
          sendMessage(chatId, "Таска не найдена!");
          return;
        }
      }
    }
  } else {
    sendMessage(chatId, "You dont have permissions!");
  }
}

function link(chatId) {
  sendMessage(chatId, "<a href=\"https://docs.google.com/spreadsheets/d/1cLSB7agk9W0uroyQTHgwnmS_ikk36HTUJLbGtmjQpec/edit?usp=sharing\">Link</a>\nДМ: <a href=\"http://neerc.ifmo.ru/wiki/index.php?title=Список_заданий_по_ДМ_2019_весна\">ДЗ</a>, <a href=\"https://docs.google.com/spreadsheets/d/15rOCbXyZVFpBVg34wLKN3j4dzdoVc3kURMFyiqZxa3M/edit#gid=0\">Табличка, </a>/another_dm\n" +
                   "АиСД: <a href=\"http://neerc.ifmo.ru/teaching/algo/year2018/tasks_36-37_sem2.pdf\">ДЗ</a>, <a href=\"https://docs.google.com/spreadsheets/d/1g3xIvGcPgE5Kch8ZwtDuJ8lmGzarN7QqA3jI8nAqOYY\">Табличка</a>\n" +
                   "Матан: <a href=\"http://vilenin.narod.ru/Mm/Books/5/book.htm\">ДЗ</a>, <a href=\"https://cloud.mail.ru/public/J9qM/D3kjhHF6u\">Табличка</a>, /another_math\n" +
                   "Программирование: <a href=\"http://www.kgeorgiy.info/courses/\">ДЗ</a>, <a href=\"https://docs.google.com/spreadsheets/d/e/2PACX-1vQyw-q3t_m9YUMGqk4zCLOdNMoJVtlyVQJv4dBsCTVIxkgNUWlMzZmmAPnad2fXVaY4FU1cJlDzTXtM/pubhtml\">Табличка</a>, <a href=\"https://www.lektorium.tv/course/2282\">Лекции</a>, /another_prog\n" +
                    "C++: <a href=\"https://github.com/itiviti-cpp/wiki/wiki\">Единая страничка</a>\n" +
                    "<a href=\"http://neerc.ifmo.ru/lgd.pdf\">Если что-то не осилил, держи в помощь</a>"
                   );
}

function alarm(chatId, message, Id) {
  val = message[7];
  if (Number(val) > 0 && Number(val) < 3) {
    setAlarm(Id, Number(val));
    sendMessage(chatId, "Alarm mode set successfully!");
    return;
  }
  sendMessage(chatId, "Unknown alarm mode!");
}

function mode(chatId, message, Id) {
  val = message[6];
  if (val == "d") {
    setMode(Id, "d");
    sendMessage(chatId, "DISKRETMode set successfully!");
    return;
  }
  if (val == "a") {
    setMode(Id, "a");
    sendMessage(chatId, "AISDMode set successfully!");
    return;
  }
  if (val == "p") {
    setMode(Id, "p");
    sendMessage(chatId, "PROGMode set successfully!");
    return;
  }
  sendMessage(chatId, "Unknown mode: " + val);
}

function bot_info(chatId) {
  sendMessage(chatId, getValue(1, 17, 5));
}

function make_log(Id, message) {
  curr = getValue(2, 7, 6) + 2;
  setValue(curr, 1, 6, getUsername(Id));
  setValue(curr, 2, 6, Date());
  setValue(curr, 3, 6, message);
  setValue(1, 7, 6, getValue(1, 7, 6) + 1);
  setValue(2, 7, 6, getValue(2, 7, 6) + 1);
}

function make_history(number, Id, subject) {
  curr = getValue(2, subject * 2 + 1, 3) + 5;
  setValue(curr, subject * 2 + 1, 3, number);
  setValue(curr, subject * 2 + 2, 3, getName(Id));
}

function isAdmin(Id) {
  if (getPermission(Id) == "Admin" || getPermission(Id) == "Special") {
    return true;
  } else {
    return false;
  }
}

function isStudent(Id) {
  if (getPermission(Id) == "Student" || getPermission(Id) == "Special") {
    return true;
  } else {
    return false;
  }
}

function isNobody(username) {
  if (getId(username) == -1) {
    return true;
  } else {
    return false;
  }
}

function getId(username) {
  var i = 2;
  while (true) {
    if (getValue(i, 2, 5) == username) {
      return getValue(i, 1, 5);
    } 
    i++;
    if (getValue(i, 1, 5) == "#") {
      return -1;
    }
  }
}

function getUsername(Id) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      return getValue(i, 2, 5);
    } 
    i++;
  }
}

function getAlarm(Id) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      return getValue(i, 8, 5);
    } 
    i++;
  }
}

function getName(Id) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      return getValue(i, 3, 5);
    }
    i++;
  }
}

function getPermission(Id) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      return getValue(i, 5, 5);
    } 
    i++;
  }
}

function getMode(Id) {
  var i = 1;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      return getValue(i, 7, 5)[1];
    } 
    i++;
  }
}

function getNumMode(Id) {
  if (getMode(Id) == "d") {
    return 0;
  }
  if (getMode(Id) == "a") {
    return 1;
  }
  if (getMode(Id) == "p") {
    return 2;
  }
  return -1;
}

function setMode(Id, mode) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      setValue(i, 7, 5, "-" + mode);
      return;
    }
    i++;
  }
}

function setAlarm(Id, alarm) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      setValue(i, 8, 5, alarm);
      return;
    }
    i++;
  }
}

function add_task(nametask, numMode) {
  var i = 6;
  while (true) {
    if (getValue(1, i, numMode) != "") {
      setValue(1, i, numMode, nametask);
      return;
    }
    i++;
    if (getValue(1, i, numMode) == "#") {
      return;
    }
  }
}

function set_chatId(Id, chatId) {
  var i = 2;
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      setValue(i, 4, 5, chatId);
      return;
    }
    i++;
  }
}

function clear_log(chatId, Id) {
  if (isAdmin(Id)) {
    curr = getValue(2, 7, 6) + 2;
    for (var i = 1; i <= curr + 5; i++) {
      setValue(curr, 1, 6, null);
      setValue(curr, 2, 6, null);
      setValue(curr, 3, 6, null);
    }
    setValue(2, 7, 6, 1);
  } else {
    sendMessage(chatId, "You dont have permissions!");
  }
}

function set_username(Id, username) {
  var i = 2;
  var user = "";
  for (var j = 14; j < username.length; j++) {
    user += username[j];
  }
  while (true) {
    if (getValue(i, 1, 5) == Id) {
      setValue(i, 2, 5, user);
      return;
    } 
    i++;
  }
}

function sendAll(message) {
  var i = 2;
  var mess = "";
  for (var j = 14; j < message.length; j++) {
    mess += message[j];
  }
  while (true) {
    if (getValue(i, 1, 5) == "#") {
      return;
    }
    if (getValue(i, 8, 5) < 3) {
      sendMessage(431427699, "p");
      var temp = String(getValue(i, 4, 5));
      sendMessage(431427699, temp + " hell " + mess);
      sendMessage(temp, mess);
    }
    i++;
  }
}

function sendMessage(chatId, message) {
  var payload = {
          'method': 'sendMessage',
          'chat_id': String(chatId),
          'text': message,
          'parse_mode': 'HTML'
        }     
        var data = {
          "method": "post",
          "payload": payload
        }
        var API_TOKEN = '691478699:AAGFR0YN4vY1FsaMyEXmskcsT4aw5ZwRtbg'
        UrlFetchApp.fetch('https://api.telegram.org/bot' + API_TOKEN + '/', data);
}