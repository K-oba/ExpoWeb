(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('ResetFinishController', ResetFinishController);

    ResetFinishController.$inject = ['$stateParams', '$timeout', 'Auth', 'LoginService','$http'];

    function ResetFinishController ($stateParams, $timeout, Auth, LoginService,$http) {
        var vm = this;

        vm.keyMissing = angular.isUndefined($stateParams.key);
        vm.confirmPassword = null;
        vm.doNotMatch = null;
        vm.error = null;
        vm.finishReset = finishReset;
        vm.login = LoginService.open;
        vm.resetAccount = {};
        vm.success = null;
        vm.userID = 0;
        $timeout(function (){angular.element('#password').focus();});

        function finishReset() {

          var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            //console.log(value);
            vm.userID =   decodeURIComponent(value.replace(/\+/g, '%20'));
//            console.log(vm.userID);
          });
            //match = url.match(/userID=(\d+)/)
            //console.log(parts);
            vm.doNotMatch = null;
            vm.error = null;
            if (vm.resetAccount.password !== vm.confirmPassword) {
                 vm.doNotMatch = 'ERROR';
             } else {
               //console.log('hey');
               var requestData ={
                 "clave": vm.confirmPassword,
                 "correo": "string",
                 "id": 0,
                 "nombre": vm.userID,
                 "rolId": 0,
                 "rolNombre": "string",
                 "standId": 0,
                 "standNombre": "string",
                 "timelines": [
                   {
                     "exposicionId": 0,
                     "id": 0
                   }
                 ]};
               var req = {
               method: 'POST',
               url: 'http://localhost:8080/api/changePassword',
               data: requestData
               };

                 $http(req).then(function(response){
                     //console.log(response.data);
                     if(response.data.id !== 0){
                       vm.success = 'OK';
                     }
                 },function(error){
                     //console.log(error);
                     vm.validEmail = true;
                 });



            //     Auth.resetPasswordFinish({key: $stateParams.key, newPassword: vm.resetAccount.password}).then(function () {
            //         vm.success = 'OK';
            //     }).catch(function () {
            //         vm.success = null;
            //         vm.error = 'ERROR';
            //     });
           }
        }
    }
})();
