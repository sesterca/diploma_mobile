package drivers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import configuration.BrowserStackConfig;
import configuration.MobileConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackMobileDriver implements WebDriverProvider {
    @Override
    public WebDriver createDriver(Capabilities capabilities){

        BrowserStackConfig browserStackConfig = ConfigFactory.create(BrowserStackConfig.class, System.getProperties());

        MobileConfig mobileConfig = ConfigFactory.create(MobileConfig.class, System.getProperties());

        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        // Set your access credentials
        mutableCapabilities.setCapability("browserstack.user", browserStackConfig.browserStackUser());
        mutableCapabilities.setCapability("browserstack.key", browserStackConfig.browserStackKey());
        // Set URL of the application under test
        mutableCapabilities.setCapability("app", browserStackConfig.browserStackApp());
        // Specify device and os_version for testing
        mutableCapabilities.setCapability("device", mobileConfig.device());
        mutableCapabilities.setCapability("os_version", mobileConfig.os());
        // Set other BrowserStack capabilities
        mutableCapabilities.setCapability("project", "browserstack_mobile");
        mutableCapabilities.setCapability("build", "browserstack-build-1");
        mutableCapabilities.setCapability("name", "android test");
        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }
    public static URL getBrowserstackUrl() {
        try {
            return new URL("http://hub.browserstack.com/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
