package de.fanero.hit;

import com.google.inject.Inject;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import de.fanero.entity.Entity;

public final class ScrollerContactListener implements ContactListener {

    @Inject
    public ScrollerContactListener() {
    }

    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void endContact(Contact contact) {

    }

    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        beginUserDataContact(userDataA, userDataB);
    }

    private void beginUserDataContact(Object userDataA, Object userDataB) {
        if (userDataA instanceof Entity && userDataB instanceof Entity) {
            Entity e1 = (Entity) userDataA;
            Entity e2 = (Entity) userDataB;

            if (!e1.isMarkedForRemoval() && !e2.isMarkedForRemoval()) {
                e1.isHitBy(e2);
                e2.isHitBy(e1);
            }
        }
    }
}