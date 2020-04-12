package com.immmus.telegram.bot.utils;

import com.immmus.telegram.bot.handlers.UpdateHandler;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.immmus.telegram.bot.utils.UpdateConditionsFlow.ConditionStatus.*;

public final class UpdateConditionsFlow {
    private final Update update;
    private ConditionStatus conditionStatus;
    private UpdateHandler currentActionHandler;

    private UpdateConditionsFlow(Update update) {
        this.update = update;
        this.conditionStatus = ON_CHECK;
    }

    public static UpdateConditionsFlow of(Update update) {
        return new UpdateConditionsFlow(update);
    }

    enum ConditionStatus {
        ON_CHECK,
        CHECKED,
        FINISH
    }

    public AfterCheck check(Predicate<Update> condition) {
        Assert.notNull(condition, "Condition arg cannot be empty");
        if (conditionStatus.equals(ON_CHECK) && condition.test(update)) {
            conditionStatus = CHECKED;
        }
        return new AfterCheck();
    }

    public class AfterCheck {
        private AfterCheck() {}
        public AfterThen then(UpdateHandler action) {
            Assert.notNull(action, "Action arg cannot be empty");
            if (conditionStatus.equals(CHECKED)) {
                conditionStatus = ConditionStatus.FINISH;
                currentActionHandler = action;
            }
            return new AfterThen();
        }
    }

    public class AfterThen {
        private AfterThen() { }
        public AfterCheck orCheck(Predicate<Update> condition) {
            return check(condition);
        }

        public Optional<BotApiMethod<?>> orElse(UpdateHandler defaultAction) {
            Assert.notNull(defaultAction, "Default action arg cannot be empty");
            if (conditionStatus.equals(FINISH) && Objects.nonNull(currentActionHandler)) {
               return currentActionHandler.execute();
            } else {
               return defaultAction.execute();
            }
        }
    }
}