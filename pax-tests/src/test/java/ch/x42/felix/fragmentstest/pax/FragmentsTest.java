package ch.x42.felix.fragmentstest.pax;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.service.packageadmin.PackageAdmin;

/** Use pax exam test runner */
@RunWith( JUnit4TestRunner.class )

/* Restart OSGi framework for each test (which is 
 * the default setting)
 */
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)

/** Example test that runs with pax exam, just
 *  checks that BundleContext is supplied and that
 *  we can install bundles
 */
public class FragmentsTest {

    // pax exam configuration, what to provision etc.
    @Configuration
    public Option[] config()
    {
        return options(
            junitBundles(),
            felix().version(System.getProperty("felix.test.version")),
            mavenBundle("ch.x42.felix.fragmentstest", "host-bundle", "0.9.9-SNAPSHOT"),
            mavenBundle("ch.x42.felix.fragmentstest", "fragment-bundle", "0.9.9-SNAPSHOT")
        );
    }
    
    @Inject
    private PackageAdmin packageAdmin;
    
    @Test
    public void testPackageAdmin()
    {
        assertNotNull("Expecting PackageAdmin to be supplied", packageAdmin);
    }
    
    @Test
    public void testPackageFromHost() {
        assertNotNull(packageAdmin.getExportedPackage("ch.x42.felix.fragmenttests.host"));
    }
    
    @Test
    public void testPackageFromFragment() {
        assertNotNull(packageAdmin.getExportedPackage("ch.x42.felix.fragmenttests.fragment"));
    }
}
