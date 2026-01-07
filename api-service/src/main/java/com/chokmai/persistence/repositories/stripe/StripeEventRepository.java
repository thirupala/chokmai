package com.chokmai.persistence.repositories.stripe;

import com.chokmai.persistence.entities.stripe.StripeEventEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
@ApplicationScoped
public class StripeEventRepository
        implements PanacheRepositoryBase<StripeEventEntity,UUID> {

    public boolean alreadyProcessed(String eventId) {
        return count("eventId", eventId) > 0;
    }
}
