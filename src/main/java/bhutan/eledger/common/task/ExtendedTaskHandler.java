package bhutan.eledger.common.task;

import am.iunetworks.lib.task.TaskHandler;

public interface ExtendedTaskHandler extends TaskHandler {
    String getTaskType();
}
