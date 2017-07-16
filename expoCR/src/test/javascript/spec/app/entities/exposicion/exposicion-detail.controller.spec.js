'use strict';

describe('Controller Tests', function() {

    describe('Exposicion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExposicion, MockDistrito, MockCategoria, MockCharla, MockAmenidades, MockBeacon, MockTimeline, MockClick, MockPregunta, MockUsuario, MockStand;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExposicion = jasmine.createSpy('MockExposicion');
            MockDistrito = jasmine.createSpy('MockDistrito');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockCharla = jasmine.createSpy('MockCharla');
            MockAmenidades = jasmine.createSpy('MockAmenidades');
            MockBeacon = jasmine.createSpy('MockBeacon');
            MockTimeline = jasmine.createSpy('MockTimeline');
            MockClick = jasmine.createSpy('MockClick');
            MockPregunta = jasmine.createSpy('MockPregunta');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockStand = jasmine.createSpy('MockStand');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Exposicion': MockExposicion,
                'Distrito': MockDistrito,
                'Categoria': MockCategoria,
                'Charla': MockCharla,
                'Amenidades': MockAmenidades,
                'Beacon': MockBeacon,
                'Timeline': MockTimeline,
                'Click': MockClick,
                'Pregunta': MockPregunta,
                'Usuario': MockUsuario,
                'Stand': MockStand
            };
            createController = function() {
                $injector.get('$controller')("ExposicionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:exposicionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
