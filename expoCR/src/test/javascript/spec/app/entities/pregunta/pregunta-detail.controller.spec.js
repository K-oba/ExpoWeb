'use strict';

describe('Controller Tests', function() {

    describe('Pregunta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPregunta, MockUsuario, MockCharla, MockExposicion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPregunta = jasmine.createSpy('MockPregunta');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockCharla = jasmine.createSpy('MockCharla');
            MockExposicion = jasmine.createSpy('MockExposicion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pregunta': MockPregunta,
                'Usuario': MockUsuario,
                'Charla': MockCharla,
                'Exposicion': MockExposicion
            };
            createController = function() {
                $injector.get('$controller')("PreguntaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:preguntaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
