# springboot-webjars 

Webjars version filter for springboot
=====================================

Strips the version from webjars paths based on the version found in pom.properties for the webjars.

For example AngularJS 1.3.14 that normally would be imported with...

    <script src="webjars/angularjs/1.3.14/angular.js"></script> 

...can also be imported without the version:

    <script src="webjars/angularjs/angular.js"></script>

Configuring Springboot
======================

Make sure the filter is discovered by SpringBoot by adding it
to the ComponentScan annotation.

    @SpringBootApplication
    @ComponentScan(basePackageClasses={WebjarVersionFilter.class})
    public class FrontMain {
        ...
    }

Stolen with pride from...
=========================

Part of the code stolen from olle.hallin@crisp.se. 
The idea of using pom.properties originates from the
dropwizard add-on https://github.com/bazaarvoice/dropwizard-webjars-bundle.
