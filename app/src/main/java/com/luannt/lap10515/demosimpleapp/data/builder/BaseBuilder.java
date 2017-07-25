package com.luannt.lap10515.demosimpleapp.data.builder;

/**
 * Created by lap10515 on 24/07/2017.
 */

public abstract class BaseBuilder<DEntity, Entity> {
    protected abstract Entity builder(DEntity dEntity);
    protected abstract DEntity dbBuilber(Entity entity);
}
