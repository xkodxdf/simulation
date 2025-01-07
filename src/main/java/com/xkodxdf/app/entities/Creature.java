package com.xkodxdf.app.entities;
/*
    Creature - абстрактный класс, наследуется от Entity.
    Существо, имеет скорость (сколько клеток может пройти за 1 ход), количество HP.
    Имеет метод makeMove() - сделать ход.
*/
public abstract class Creature extends Entity {

    private int speed;
    private int healtPoints;

    protected abstract void makeMove();
}
