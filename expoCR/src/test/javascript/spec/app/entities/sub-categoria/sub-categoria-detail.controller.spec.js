'use strict';

describe('Controller Tests', function() {

    describe('SubCategoria Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSubCategoria, MockCategoria, MockBrouchure, MockClick, MockBeacon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSubCategoria = jasmine.createSpy('MockSubCategoria');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockBrouchure = jasmine.createSpy('MockBrouchure');
            MockClick = jasmine.createSpy('MockClick');
            MockBeacon = jasmine.createSpy('MockBeacon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SubCategoria': MockSubCategoria,
                'Categoria': MockCategoria,
                'Brouchure': MockBrouchure,
                'Click': MockClick,
                'Beacon': MockBeacon
            };
            createController = function() {
                $injector.get('$controller')("SubCategoriaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expoCrApp:subCategoriaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
