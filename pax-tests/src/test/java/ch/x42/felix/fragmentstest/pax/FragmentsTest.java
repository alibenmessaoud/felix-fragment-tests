package ch.x42.felix.fragmentstest.pax;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.service.packageadmin.PackageAdmin;

@RunWith(PaxExam.class)
public class FragmentsTest {

    @Configuration
    public Option[] config()
    {
        return options(
            junitBundles(),
            mavenBundle("ch.x42.felix.fragmentstest", "host-bundle", "0.9.9-SNAPSHOT"),
            mavenBundle("ch.x42.felix.fragmentstest", "fragment-bundle", "0.9.9-SNAPSHOT").noStart()
        );
    }
    
    @Inject
    private PackageAdmin packageAdmin;
    
    @Test
    public void testPackageFromHost() {
        assertNotNull(
                "Expecting package provided by host bundle to be found",
                packageAdmin.getExportedPackage("ch.x42.felix.fragmenttests.host"));
    }
    
    @Test
    public void testPackageFromFragment() {
        assertNotNull(
                "Expecting package provided by fragment bundle to be found",
                packageAdmin.getExportedPackage("ch.x42.felix.fragmenttests.fragment"));
    }
}