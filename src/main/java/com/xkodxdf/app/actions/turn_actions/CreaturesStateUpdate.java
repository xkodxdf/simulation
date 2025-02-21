package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.CreatureState;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.worldmap.WorldMapManagement;

import java.util.List;

public class CreaturesStateUpdate extends TurnAction {

    @Override
    public void process(WorldMapManagement mapManager) {
        roamStateUpdate(mapManager.getEntitiesByType(Creature.class));
        forageStateUpdate(mapManager.getEntitiesByType(Creature.class));
    }

    private void roamStateUpdate(List<Creature> creatures) {
        creatures.stream()
                .filter(this::shouldRoam)
                .forEach(creature -> creature.setState(CreatureState.ROAM));
    }

    private boolean shouldRoam(Creature creature) {
        return (!creature.isDead()) && (creature.isHungry());
    }

    private void forageStateUpdate(List<Creature> creatures) {
        creatures.stream().
                filter(this::shouldForage)
                .forEach(creature -> creature.setState(CreatureState.FORAGE));
    }

    private boolean shouldForage(Creature creature) {
        return (!creature.isDead())
                && ((creature.isHungry()) || (!creature.isOnFullHp()));
    }
}
