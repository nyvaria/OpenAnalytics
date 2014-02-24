OpenAnalytics Changelog
=======================

OpenAnalytics v1.0.03 - 23 February 2014
----------------------------------------

 - Fixed NullPointerException on Player Kick

OpenAnalytics v1.0.02 - 14 February 2014
----------------------------------------

 - Code refactoring
 - Updated SignShop e-commerce hits to use shop owner for Transaction Affiliation, to use the block name as the Item Code and to calculate a per item price
 - Now submitting including player IP address via new IP Override parameter of the Google Analytics Measurement (improve metrics shown in Google Analytics console, all anonymous still)
 - Now working again with Java 1.6

OpenAnalytics v1.0.01 - 02 February 2014
----------------------------------------

 - Initial release with required functionality added
 - Added "/analytics admin" command for setting config parameters in game
 - Added "/analytics optout" and "/analytics optin" to allow players to opt out/in
 - Added permissions relating to these commands
 - Refactored all common code out to NyvariaComponents library
 - Added support for SignShop (not sure if Google Analytics is picking it up correctly)
 - Added pretty player names, world names, etc

