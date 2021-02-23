import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals(sendToGoogleAnalytics);

// https://github.com/GoogleChrome/web-vitals#send-the-results-to-google-analytics
// Using gtag.js (Universal Analytics)
function sendToGoogleAnalytics({name, delta, id}) {
  // Assumes the global `gtag()` function exists, see:
  // https://developers.google.com/analytics/devguides/collection/gtagjs
  window.gtag('event', name, {
    event_category: 'Web Vitals',
    // The `id` value will be unique to the current page load. When sending
    // multiple values from the same page (e.g. for CLS), Google Analytics can
    // compute a total by grouping on this ID (note: requires `eventLabel` to
    // be a dimension in your report).
    event_label: id,
    // Google Analytics metrics must be integers, so the value is rounded.
    // For CLS the value is first multiplied by 1000 for greater precision
    // (note: increase the multiplier for greater precision if needed).
    value: Math.round(name === 'CLS' ? delta * 1000 : delta),
    // Use a non-interaction event to avoid affecting bounce rate.
    non_interaction: true,
  });
}


// Using gtag.js (Google Analytics 4)
function sendToGoogleAnalyticsGA4({name, delta, value, id}) {
  // Assumes the global `gtag()` function exists, see:
  // https://developers.google.com/analytics/devguides/collection/ga4
  window.gtag('event', name, {
    // Use the metric delta as the event's value parameter.
    value: delta,
    // Everything below is a custom event parameter.
    web_vitals_metric_id: id, // Needed to aggregate events.
    web_vitals_metric_value: value, // Optional
    // Any additional params or metadata (e.g. debug information) can be
    // set here as well, within the following limitations:
    // https://support.google.com/analytics/answer/9267744
  });
}
