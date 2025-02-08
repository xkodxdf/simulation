package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.CreatureState;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.map.WorldMapManage;

import java.util.List;

public class CreaturesStateUpdate extends TurnAction {

    @Override
    public void process(WorldMapManage mapManager) {
        deathStateUpdate(mapManager.getEntitiesByType(Creature.class));
        roamStateUpdate(mapManager.getEntitiesByType(Creature.class));
        forageStateUpdate(mapManager.getEntitiesByType(Creature.class));
    }

    private void roamStateUpdate(List<Creature> creatures) {
        creatures.stream()
                .filter(this::shouldRoam)
                .forEach(creature -> creature.setState(CreatureState.ROAM));
    }

    private void forageStateUpdate(List<Creature> creatures) {
        creatures.stream().
                filter(this::shouldForage)
                .forEach(creature -> creature.setState(CreatureState.FORAGE));
    }

    private void deathStateUpdate(List<Creature> creatures) {
        creatures.stream()
                .filter(Creature::isDead)
                .forEach(creature -> creature.setState(CreatureState.DEATH));
    }

    private boolean shouldRoam(Creature creature) {
        return creature.getState().isAlive() && creature.isHungry();
    }

    private boolean shouldForage(Creature creature) {
        return (creature.getState().isAlive())
                && ((creature.isHungry()) || (!creature.isOnFullHp()));
    }
}
