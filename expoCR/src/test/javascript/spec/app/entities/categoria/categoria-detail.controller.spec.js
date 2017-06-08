'use strict';

describe('Controller Tests', function() {

    describe('Categoria Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCategoria, MockExposicion, MockSubCategoria;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockExposicion = jasmine.createSpy('MockExposicion');
            MockSubCategoria = jasmine.createSpy('MockSubCategoria');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Categoria': MockCategoria,
                'Exposicion': MockExposicion,
                'SubCategoria': MockSubCategoria
            };
            createController = function() {
                $injector.get('$controller')("CategoriaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:categoriaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
