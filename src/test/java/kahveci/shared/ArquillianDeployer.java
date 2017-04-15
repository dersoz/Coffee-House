package kahveci.shared;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public final class ArquillianDeployer {

    private ArquillianDeployer() {
    }

    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "dersoz.ska.business")
                .addPackages(true, "kahveci")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/jpql")
                .addAsResource("project-defaults.yml")
//                .addAsWebInfResource(getDataSourceConfig(), "jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        File[] runtimeDependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();
        File[] deps = new File[runtimeDependencies.length];
        for (int i = 0; i < runtimeDependencies.length; i++) {
            File file = runtimeDependencies[i];
            if (file.getName().contains("kahveci-domain")) {
                File f = new File(file.getParent(), "kahveci-domain-1.0-SNAPSHOT.jar");
                file.renameTo(f);
                file = f;
            }
            deps[i] = file;
        }
        war.addAsLibraries(deps);
        printArchive(war);
        return war;
    }

    public static void printArchive(Archive<?> archive) {
        archive.getContent().values().forEach(d -> System.out.println("\t" + d));
    }

}
