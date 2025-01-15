package com.xkodxdf.app.actions.turn_actions;

import com.xkodxdf.app.entities.CreatureState;
import com.xkodxdf.app.entities.base.Creature;
import com.xkodxdf.app.exceptions.InvalidParametersException;
import com.xkodxdf.app.map.Manager;

import java.util.List;

public class CreaturesStateUpdater extends TurnActions {

    @Override
    public void process(Manager mapManager) throws InvalidParametersException {
        deathStateUpdate(mapManager.getCreatures());
        escapeStateUpdate(mapManager.getCreatures());
        roamStateUpdate(mapManager.getCreatures());
        forageStateUpdate(mapManager.getCreatures());
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

    private void escapeStateUpdate(List<Creature> creatures) {
        creatures.stream()
                .filter(this::shouldEscape)
                .forEach(creature -> creature.setState(CreatureState.ESCAPE));
    }

    private void deathStateUpdate(List<Creature> creatures) {
        creatures.stream()
                .filter(creature -> creature.getHealthPoints() <= 0)
                .forEach(creature -> creature.setState(CreatureState.DEATH));
    }


    private boolean shouldRoam(Creature creature) {
        return isSafe(creature) && creature.getHunger() < 25;
    }

    private boolean shouldForage(Creature creature) {
        return isSafe(creature) && creature.getHunger() >= 25;
    }

    private boolean shouldEscape(Creature creature) {
        return creature.getState().isAlive() && creature.isInDanger();
    }

    private boolean isSafe(Creature creature) {
        CreatureState state = creature.getState();

        return state.isAlive() && !state.isEscaping();
    }
}
