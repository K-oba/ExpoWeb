'use strict';

describe('Controller Tests', function() {

    describe('Usuario Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUsuario, MockTimeline, MockPregunta, MockStand, MockRol;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockTimeline = jasmine.createSpy('MockTimeline');
            MockPregunta = jasmine.createSpy('MockPregunta');
            MockStand = jasmine.createSpy('MockStand');
            MockRol = jasmine.createSpy('MockRol');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Usuario': MockUsuario,
                'Timeline': MockTimeline,
                'Pregunta': MockPregunta,
                'Stand': MockStand,
                'Rol': MockRol
            };
            createController = function() {
                $injector.get('$controller')("UsuarioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:usuarioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
