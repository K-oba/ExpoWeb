(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('RequestResetController', RequestResetController);

    RequestResetController.$inject = ['$timeout', 'Auth','$http'];

    function RequestResetController ($timeout, Auth,$http) {
        var vm = this;

        vm.error = null;
        vm.errorEmailNotExists = null;
        vm.requestReset = requestReset;
        vm.resetAccount = {};
        vm.success = null;
        vm.validEmail = false;

        $timeout(function (){angular.element('#email').focus();});

        function requestReset (email) {
            var requestData ={email:email};
            var req = {
            method: 'POST',
            url: 'http://localhost:8080/api/requestPasswordReset',
            data: email
            };
            try {
              $http(req).then(function(response){
                  //console.log(response.data);
                  vm.success = 'OK';
              },function(error){
                  console.log(error);
                  vm.validEmail = true;
              });
            }
            catch(err) {
                //console.console.log(err);
            }


            // Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
            //     vm.success = 'OK';
            // }).catch(function (response) {
            //     vm.success = null;
            //     if (response.status === 400 && response.data === 'email address not registered') {
            //         vm.errorEmailNotExists = 'ERROR';
            //     } else {
            //         vm.error = 'ERROR';
            //     }
            // });
        }
    }
})();
