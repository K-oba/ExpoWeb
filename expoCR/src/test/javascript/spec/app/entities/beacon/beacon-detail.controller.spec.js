'use strict';

describe('Controller Tests', function() {

    describe('Beacon Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBeacon, MockExposicion, MockStand, MockSubCategoria;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBeacon = jasmine.createSpy('MockBeacon');
            MockExposicion = jasmine.createSpy('MockExposicion');
            MockStand = jasmine.createSpy('MockStand');
            MockSubCategoria = jasmine.createSpy('MockSubCategoria');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Beacon': MockBeacon,
                'Exposicion': MockExposicion,
                'Stand': MockStand,
                'SubCategoria': MockSubCategoria
            };
            createController = function() {
                $injector.get('$controller')("BeaconDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:beaconUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
