package ru.yofik.bpms.camunda;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class CamundaProcessInfo {
    public final String id;
    public final int version;
    public final String deploymentId;
}
