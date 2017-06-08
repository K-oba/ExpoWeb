'use strict';

describe('Controller Tests', function() {

    describe('Amenidades Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAmenidades, MockExposicion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAmenidades = jasmine.createSpy('MockAmenidades');
            MockExposicion = jasmine.createSpy('MockExposicion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Amenidades': MockAmenidades,
                'Exposicion': MockExposicion
            };
            createController = function() {
                $injector.get('$controller')("AmenidadesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:amenidadesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
