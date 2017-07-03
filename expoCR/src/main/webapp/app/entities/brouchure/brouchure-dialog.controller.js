(function() {
    'use strict';

    angular
        .module('expoCrApp')
        .controller('BrouchureDialogController', BrouchureDialogController);

    BrouchureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Brouchure', 'Stand', 'SubCategoria'];

    function BrouchureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Brouchure, Stand, SubCategoria) {
        var vm = this;

        vm.brouchure = entity;
        vm.clear = clear;
        vm.save = save;
        vm.setUrl = setUrl;
        vm.stands = Stand.query();
        vm.subcategorias = SubCategoria.query();
        vm.url ="";


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var url = document.getElementById('url');
            //var url = document.getElementById('url').files[0];
            //url.value.replace("C:\\fakepath\\", "");
            //console.log(url.value.replace("C:\\fakepath\\", ""));
              //console.log(vm.brouchure);
            vm.brouchure.urlimagen = "sdfsdfs";
            //console.log(vm.brouchure);
            if (vm.brouchure.nombre !== null) {
                Brouchure.update(vm.brouchure, onSaveSuccess, onSaveError);
            } else {
                Brouchure.save(vm.brouchure, onSaveSuccess, onSaveError);
            }
        }
        function setUrl (url){
          console.log(url);
        }
        function onSaveSuccess (result) {
            $scope.$emit('expoCrApp:brouchureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
        // vm.readURL = function(input) {
        //        if (input.files && input.files[0]) {
        //            var reader = new FileReader();
        //
        //            reader.onload = function (e) {
        //                $('#blah')
        //                    .attr('src', e.target.result);
        //            };
        //
        //            reader.readAsDataURL(input.files[0]);
        //        }
        //    }
    //     $("#url").change(function() {
    // var file = this.files[0];
    // var imagefile = file.type;
    // var imagesize = file.size;
    // console.log(imagesize);
    // var match= ["image/jpeg","image/png","image/jpg","image/gif"];
    // $('.upload-submit').prop('disabled',false).css('opacity',1);
    // if(!((imagefile==match[0]) || (imagefile==match[1]) || (imagefile==match[2]) || (imagefile==match[3])))
    // {
    //
    //     $("#message").html("<p id='error' style='color:red;'>Please Select A valid Image File</p>"+"<h4  style='color:red;'>Note</h4>"+"<span id='error_message'  style='color:red;'>Only jpeg, jpg, gif and png Images type allowed</span>");
    //     return false;
    // } else if (imagesize > 6000000){
    //
    //     $("#message").html("<p id='error' style='color:red;'>Your file size is higher than the allowed size (6MB)</p>");
    //     return false;
    // }
    // else
    // {
    //     var reader = new FileReader();
    //     reader.onload = imageIsLoaded;
    //     reader.readAsDataURL(this.files[0]);
    //     var imgData = reader.onload;        }
// });

    }
})();
