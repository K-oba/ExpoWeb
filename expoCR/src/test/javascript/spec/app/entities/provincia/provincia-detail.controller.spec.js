'use strict';

describe('Controller Tests', function() {

    describe('Provincia Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProvincia, MockCanton;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProvincia = jasmine.createSpy('MockProvincia');
            MockCanton = jasmine.createSpy('MockCanton');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Provincia': MockProvincia,
                'Canton': MockCanton
            };
            createController = function() {
                $injector.get('$controller')("ProvinciaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:provinciaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
