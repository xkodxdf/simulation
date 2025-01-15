package com.xkodxdf.app.actions;

import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Manager;

/*
    Actions
    Action - действие, совершаемое над миром. Например - сходить всеми существами.
    Это действие итерировало бы существ и вызывало каждому makeMove().
    Каждое действие описывается отдельным классом и совершает операции над картой.
    Симуляция содержит 2 массива действий:
       - initActions - действия, совершаемые перед стартом симуляции. Пример - расставить объекты и существ на карте
       - turnActions - действия, совершаемые каждый ход. Примеры - передвижение существ, добавить травы или травоядных,
         если их осталось слишком мало
*/
public interface Actions {

    void process(Manager mapManager) throws InvalidParametersException;
}
