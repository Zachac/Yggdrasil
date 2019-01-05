animation = {}

animation.init = function () {
    animation.entityEasing = animation.getEasingFunction();
}

animation.getEasingFunction = function () {
    let result = new BABYLON.ExponentialEase(2);

    result.setEasingMode(BABYLON.EasingFunction.EASINGMODE_EASEOUT);

    return result;
}
