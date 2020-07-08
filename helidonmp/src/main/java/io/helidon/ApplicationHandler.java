package io.helidon;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.helidon.services.WorldService;
import io.helidon.services.FortuneService;
import io.helidon.services.JsonService;
import io.helidon.services.PlaintextService;

@ApplicationScoped
@ApplicationPath("/")
public class ApplicationHandler extends Application {
  public Set<Class<?>> getClasses() {
    Set<Class<?>> serviceClass = new HashSet<Class<?>>();
    serviceClass.add(JsonService.class);
    serviceClass.add(PlaintextService.class);
    serviceClass.add(WorldService.class);
    serviceClass.add(FortuneService.class);
    return serviceClass;
  }
}