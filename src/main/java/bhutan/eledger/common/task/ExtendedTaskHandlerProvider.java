package bhutan.eledger.common.task;

import am.iunetworks.lib.task.Task;
import am.iunetworks.lib.task.TaskHandler;
import am.iunetworks.lib.task.TaskHandlerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class ExtendedTaskHandlerProvider implements TaskHandlerProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedTaskHandlerProvider.class);

    private final Collection<ExtendedTaskHandler> extendedTaskHandlers;

    ExtendedTaskHandlerProvider(Collection<ExtendedTaskHandler> extendedTaskHandlers) {
        this.extendedTaskHandlers = extendedTaskHandlers;
    }

    @Override
    public TaskHandler provide(Task task) {
        LOGGER.debug("Providing handler for task: {}.", task);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Handler's task types: {}.", extendedTaskHandlers.stream().map(ExtendedTaskHandler::getTaskType).toList());
        }

        return extendedTaskHandlers.stream()
                .filter(eth -> task.getTaskType().equals(eth.getTaskType()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Task handler for task type: [" + task.getTaskType() + "] not found."));
    }
}
