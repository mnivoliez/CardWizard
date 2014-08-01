var gulp = require('gulp'),
    rimraf = require('gulp-rimraf'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    jshint = require('gulp-jshint'),
    processhtml = require('gulp-processhtml'),
    cssmin = require('gulp-minify-css'),
    gulpfilter = require('gulp-filter'),
    debug = require('gulp-debug'),
    ngmin = require('gulp-ngmin'),
    runSequence = require('run-sequence');

var paths = {
  htmlsrc: 'src/index.html',
  htmlprod: 'index.html',
  js: {
    app: 'src/app/**/*.js',
    lib: ['src/lib/jquery/dist/jquery.min.js',
      'src/lib/jquery.debouncedresize/js/jquery.debouncedresize.js',
      'src/lib/jquery-ui/jquery-ui.min.js',
      'src/lib/underscore/underscore.js',
      'src/lib/underscore.string/dist/underscore.string.min.js',
      'src/lib/bootstrap/dist/js/bootstrap.min.js',
      'src/lib/angular/angular.js',
      'src/lib/angular-ui-slider/src/slider.js',
      'src/lib/angular-route/angular-route.min.js',
      'src/lib/angular-resource/angular-resource.min.js',
      'src/lib/angular-bootstrap/ui-bootstrap-tpls.min.js',
      'src/lib/angular-translate/angular-translate.min.js',
      'src/lib/angular-cookies/angular-cookies.js',
      'src/lib/select2/select2.min.js',
      'src/lib/angular-ui-select2/src/select2.js',
      'src/lib/bootstrap-switch/dist/js/bootstrap-switch.min.js',
      'src/lib/angular-bootstrap-switch/dist/angular-bootstrap-switch.min.js',
      'src/lib/greensock/src/minified/plugins/CSSPlugin.min.js',
      'src/lib/greensock/src/minified/easing/EasePack.min.js',
      'src/lib/greensock/src/minified/TweenLite.min.js',
      'src/lib/angular-ui-sortable/sortable.min.js'],
    min: 'app.min.js'
  },
  css: {
    app: 'src/css/**/*.css',
    lib: ['src/lib/bootstrap/dist/css/bootstrap.min.css',
      'src/lib/bootstrap/dist/css/bootstrap-theme.min.css',
      'src/lib/select2/select2.css',
      'src/lib/jquery-ui/themes/smoothness/jquery-ui.min.css',
      'src/lib/select2-bootstrap-css/select2-bootstrap.css',
      'src/lib/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css',
      'src/lib/font-awesome/css/font-awesome.min.css'],
    min: 'app.min.css'
  },
  assets: ['src/img*/**', 'src/app*/**/*.tpl.html', 'src/lib/bootstrap/fonts*/*',
    'src/lib/angular-cookies/angular-cookies.min.js.map', 'src/lib/angular-cookies/angular-cookies.js', 'src/lib/jquery-ui/themes/smoothness/images*/*',
    'src/lib/font-awesome/fonts*/*', 'src/lib/select2/select2-spinner.gif', 'src/lib/select2/*.png', 'src/robots.txt'],
  dest: './dist/'
};

gulp.task('clean-task', function () {
  return gulp.src(paths.dest, { read: false })
      .pipe(rimraf());
});

gulp.task('html-task', function () {
  return gulp.src(paths.htmlsrc)
      .pipe(processhtml(paths.htmlprod))
      .pipe(gulp.dest(paths.dest));
});

gulp.task('js-check-task', function () {
  return gulp.src(paths.js.app)
      .pipe(jshint())
      .pipe(jshint.reporter('default'));
});

gulp.task('js-package-task', function () {
  var ngfilter = gulpfilter(["**/app/**/*.js"]);
  var filter = gulpfilter(["**/*.js", "!**/*.min.js"]);
  return gulp.src(paths.js.lib.concat(paths.js.app), {base: './src'})
      .pipe(ngfilter)
      .pipe(ngmin())
      .pipe(ngfilter.restore())
      .pipe(filter)
      //.pipe(uglify())
      .pipe(filter.restore())
      .pipe(concat(paths.js.min, {newLine: '\r\n'}))
      .pipe(gulp.dest(paths.dest));
});

gulp.task('css-package-task', function () {
  var filter = gulpfilter(["**/*.css", "!**/*.min.css"]);
  return gulp.src(paths.css.lib.concat(paths.css.app))
      .pipe(filter)
      .pipe(cssmin())
      .pipe(filter.restore())
      .pipe(concat(paths.css.min, {newLine: '\r\n'}))
      .pipe(gulp.dest(paths.dest));
});

gulp.task('copy-assets-task', function () {
  return gulp.src(paths.assets)
      .pipe(gulp.dest(paths.dest));
});

gulp.task('build', function (callback) {
  runSequence('clean-task', 'html-task', 'js-check-task', 'js-package-task', 'css-package-task', 'copy-assets-task',
      callback);
});
