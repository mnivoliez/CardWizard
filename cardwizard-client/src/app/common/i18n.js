cardwizardApp.config(["$translateProvider", function ($translateProvider) {
  $translateProvider.translations('en', {
    /** Header **/
    APP_NAME: 'CardWizard'
  });
  $translateProvider.preferredLanguage('en');
}]);
