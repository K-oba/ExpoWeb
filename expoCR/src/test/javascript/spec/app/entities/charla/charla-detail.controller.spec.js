'use strict';

describe('Controller Tests', function() {

    describe('Charla Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCharla, MockExposicion, MockPregunta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCharla = jasmine.createSpy('MockCharla');
            MockExposicion = jasmine.createSpy('MockExposicion');
            MockPregunta = jasmine.createSpy('MockPregunta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Charla': MockCharla,
                'Exposicion': MockExposicion,
                'Pregunta': MockPregunta
            };
            createController = function() {
                $injector.get('$controller')("CharlaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:charlaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
