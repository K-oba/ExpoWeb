'use strict';

describe('Controller Tests', function() {

    describe('Canton Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCanton, MockProvincia, MockDistrito;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCanton = jasmine.createSpy('MockCanton');
            MockProvincia = jasmine.createSpy('MockProvincia');
            MockDistrito = jasmine.createSpy('MockDistrito');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Canton': MockCanton,
                'Provincia': MockProvincia,
                'Distrito': MockDistrito
            };
            createController = function() {
                $injector.get('$controller')("CantonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:cantonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
