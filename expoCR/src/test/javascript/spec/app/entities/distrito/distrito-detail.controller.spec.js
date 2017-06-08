'use strict';

describe('Controller Tests', function() {

    describe('Distrito Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDistrito, MockCanton, MockExposicion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDistrito = jasmine.createSpy('MockDistrito');
            MockCanton = jasmine.createSpy('MockCanton');
            MockExposicion = jasmine.createSpy('MockExposicion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Distrito': MockDistrito,
                'Canton': MockCanton,
                'Exposicion': MockExposicion
            };
            createController = function() {
                $injector.get('$controller')("DistritoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:distritoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
